package kanban.utils;

public class TempStoreUtil {
    private Object item;
    private TempStoreUtil() {}

    public static TempStoreUtil getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final TempStoreUtil INSTANCE = new TempStoreUtil();
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public Object getItem() {
        return this.item;
    }
}
