package restaurantmenumanager;

/**
 * Thrown when evaluation data is invalid (e.g. rating out of range) or when a
 * saved evaluation record can't be parsed. Unchecked on purpose: other
 * members' classes (e.g. Main) can call Evaluation's constructor without
 * being forced to add a try/catch, while EvaluationManager still catches it
 * internally to demonstrate proper exception handling.
 */
public class InvalidEvaluationException extends RuntimeException {

    public InvalidEvaluationException(String message) {
        super(message);
    }

    public InvalidEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}