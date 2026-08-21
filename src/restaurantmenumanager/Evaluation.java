package restaurantmenumanager;

/**
 * Represents one evaluation (rating + feedback) submitted for a MenuItem.
 * menuItemId must match the "id" of the MenuItem being evaluated.
 */
public class Evaluation {

    private int evaluationId;
    private int menuItemId;
    private int rating;      // 1 (worst) to 5 (best)
    private String feedback;

    public Evaluation(int evaluationId, int menuItemId, int rating, String feedback) {
        setEvaluationId(evaluationId);
        setMenuItemId(menuItemId);
        setRating(rating);
        setFeedback(feedback);
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new InvalidEvaluationException("Rating must be between 1 and 5 (received " + rating + ").");
        }
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = (feedback == null) ? "" : feedback.trim();
    }

    public String getFeedbackType() {
        if (rating >= 4) return "POSITIVE";
        if (rating <= 2) return "NEGATIVE";
        return "NEUTRAL";
    }

    public String toFileLine() {
        String safeFeedback = feedback.replace(",", ";");
        return evaluationId + "," + menuItemId + "," + rating + "," + safeFeedback;
    }

    public static Evaluation fromFileLine(String line) {
        String[] parts = line.split(",", 4);
        if (parts.length < 4) {
            throw new InvalidEvaluationException("Malformed evaluation record: " + line);
        }
        try {
            int evalId = Integer.parseInt(parts[0].trim());
            int itemId = Integer.parseInt(parts[1].trim());
            int rating = Integer.parseInt(parts[2].trim());
            String feedback = parts[3];
            return new Evaluation(evalId, itemId, rating, feedback);
        } catch (NumberFormatException e) {
            throw new InvalidEvaluationException("Invalid number in evaluation record: " + line, e);
        }
    }

    @Override
    public String toString() {
        return String.format("Evaluation #%d -> Item #%d | Rating: %d/5 (%s) | Feedback: %s",
                evaluationId, menuItemId, rating, getFeedbackType(),
                feedback.isEmpty() ? "-" : feedback);
    }
}