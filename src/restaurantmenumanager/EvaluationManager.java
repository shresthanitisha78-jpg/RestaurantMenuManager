package restaurantmenumanager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the evaluation lifecycle for menu items: recording ratings/feedback,
 * calculating a status per item, and applying rewards/penalties. Evaluations
 * are linked to a MenuItem via menuItemId, which must match MenuItem's id.
 *
 * Note on file I/O: loadFromFile/saveToFile below are provided as a working
 * fallback so this class can be tested standalone against evaluations.txt.
 * If FileManager.java ends up owning all file reads/writes for the group
 * project, Main can call FileManager instead and just feed the loaded lines
 * into Evaluation.fromFileLine(...) / addEvaluation(...).
 */
public class EvaluationManager {

    private final LinkedList<Evaluation> evaluations = new LinkedList<>();
    private final Map<Integer, Integer> pointsByMenuItemId = new HashMap<>();

    private static final int REWARD_POINTS = 10;
    private static final int PENALTY_POINTS = -5;

    private int nextEvaluationId = 1;

    // ---------------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------------

    /** Adds a new evaluation for a menu item and auto-applies reward/penalty. */
    public Evaluation addEvaluation(int menuItemId, int rating, String feedback) {
        try {
            Evaluation eval = new Evaluation(nextEvaluationId, menuItemId, rating, feedback);
            evaluations.add(eval);
            nextEvaluationId++;

            if (rating >= 4) {
                processReward(menuItemId);
            } else if (rating <= 2) {
                processPenalty(menuItemId);
            }
            return eval;
        } catch (InvalidEvaluationException e) {
            System.out.println("Could not add evaluation: " + e.getMessage());
            return null;
        }
    }

    // ---------------------------------------------------------------
    // READ
    // ---------------------------------------------------------------

    public void viewEvaluations() {
        if (evaluations.isEmpty()) {
            System.out.println("No evaluations recorded yet.");
            return;
        }
        for (Evaluation e : evaluations) {
            System.out.println(e);
        }
    }

    public List<Evaluation> getEvaluationsForItem(int menuItemId) {
        return evaluations.stream()
                .filter(e -> e.getMenuItemId() == menuItemId)
                .collect(Collectors.toList());
    }

    /** Average rating + status label ("Excellent", "Good", "Average", "Needs Review") for one item. */
    public String calculateStatus(int menuItemId) {
        List<Evaluation> matches = getEvaluationsForItem(menuItemId);
        if (matches.isEmpty()) {
            return "Item #" + menuItemId + ": no evaluations recorded yet.";
        }
        double avg = matches.stream().mapToInt(Evaluation::getRating).average().orElse(0);
        String status;
        if (avg >= 4.5) status = "Excellent";
        else if (avg >= 3.5) status = "Good";
        else if (avg >= 2.5) status = "Average";
        else status = "Needs Review";

        int points = pointsByMenuItemId.getOrDefault(menuItemId, 0);
        return String.format("Item #%d: avg rating %.2f/5 (%d reviews) -> %s | reward/penalty points: %d",
                menuItemId, avg, matches.size(), status, points);
    }

    // ---------------------------------------------------------------
    // REWARD / PENALTY
    // ---------------------------------------------------------------

    public void processReward(int menuItemId) {
        pointsByMenuItemId.merge(menuItemId, REWARD_POINTS, Integer::sum);
        System.out.println("Reward applied to item #" + menuItemId + " (+" + REWARD_POINTS + " points).");
    }

    public void processPenalty(int menuItemId) {
        pointsByMenuItemId.merge(menuItemId, PENALTY_POINTS, Integer::sum);
        System.out.println("Penalty applied to item #" + menuItemId + " (" + PENALTY_POINTS
                + " points). Flagged for review.");
    }

    // ---------------------------------------------------------------
    // UPDATE / DELETE
    // ---------------------------------------------------------------

    public boolean updateEvaluation(int evaluationId, int newRating, String newFeedback) {
        for (Evaluation e : evaluations) {
            if (e.getEvaluationId() == evaluationId) {
                try {
                    e.setRating(newRating);
                    e.setFeedback(newFeedback);
                    return true;
                } catch (InvalidEvaluationException ex) {
                    System.out.println("Could not update evaluation: " + ex.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    public boolean deleteEvaluation(int evaluationId) {
        return evaluations.removeIf(e -> e.getEvaluationId() == evaluationId);
    }

    // ---------------------------------------------------------------
    // FILE I/O (fallback - see class note above)
    // ---------------------------------------------------------------

    public int loadFromFile(String path) {
        int loaded = 0;
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("No existing file found at " + path + " - starting with an empty list.");
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                try {
                    Evaluation eval = Evaluation.fromFileLine(line);
                    evaluations.add(eval);
                    nextEvaluationId = Math.max(nextEvaluationId, eval.getEvaluationId() + 1);
                    if (eval.getRating() >= 4) {
                        pointsByMenuItemId.merge(eval.getMenuItemId(), REWARD_POINTS, Integer::sum);
                    } else if (eval.getRating() <= 2) {
                        pointsByMenuItemId.merge(eval.getMenuItemId(), PENALTY_POINTS, Integer::sum);
                    }
                    loaded++;
                } catch (InvalidEvaluationException e) {
                    System.out.println("Skipped bad record: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file '" + path + "': " + e.getMessage());
        }
        return loaded;
    }

    public void saveToFile(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Evaluation eval : evaluations) {
                writer.write(eval.toFileLine());
                writer.newLine();
            }
            System.out.println("Saved " + evaluations.size() + " evaluation(s) to " + path);
        } catch (IOException e) {
            System.out.println("Error writing file '" + path + "': " + e.getMessage());
        }
    }

    public void saveQueryResults(List<Evaluation> results, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Evaluation eval : results) {
                writer.write(eval.toFileLine());
                writer.newLine();
            }
            System.out.println("Saved " + results.size() + " query result(s) to " + path);
        } catch (IOException e) {
            System.out.println("Error writing query results to '" + path + "': " + e.getMessage());
        }
    }

    public LinkedList<Evaluation> getEvaluations() {
        return evaluations;
    }
}
