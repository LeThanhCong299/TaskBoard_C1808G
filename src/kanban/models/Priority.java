package kanban.models;

public enum Priority {

    LOW {
        @Override
        public String toString() {
            return "Low";
        }
    },
    MEDIUM {
        @Override
        public String toString() {
            return "Medium";
        }
    },
    HIGH {
        @Override
        public String toString() {
            return "High";
        }
    }
}
