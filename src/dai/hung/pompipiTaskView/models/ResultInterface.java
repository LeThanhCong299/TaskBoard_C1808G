package dai.hung.pompipiTaskView.models;

import java.util.Map;

/**
 * <h1>Result interface</h1>
 * A callback that will be called on finish of http request.
 *   @author nchp
 *   @version 1.1.0
 */
public interface ResultInterface {
    void onFinish(Map result, String error);
}
