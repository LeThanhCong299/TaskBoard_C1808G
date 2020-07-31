package kanban.models;

public enum TaskFormMode {
    ADD {
        @Override
        public String toString() {
            return "Add";
        }
    },
    EDIT {
        @Override
        public String toString() {
            return "Edit";
        }
    };

    public String getFormTitle()
    {
        return this.toString() + " Task";
    }
}
