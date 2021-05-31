package personenkartei;

public enum PersonField {
    ID,
    FIRSTNAME {
        @Override
        public String toString() {
            return "Vorname";
        }
    },
    LASTNAME {
        @Override
        public String toString() {
            return "Nachname";
        }
    },
    BIRTHDAY {
        @Override
        public String toString() {
            return "Gebrutsdatum";
        }
    },
    STREET {
        @Override
        public String toString() {
            return "Straße";
        }
    },
    HOUSENUMBER {
        @Override
        public String toString() {
            return "Hausnummer";
        }
    },
    ZIP {
        @Override
        public String toString() {
            return "ZIP";
        }
    },
    CITY {
        @Override
        public String toString() {
            return "Stadt";
        }
    },
    EMAIL {
        @Override
        public String toString() {
            return "E-Mail Adresse";
        }
    }
}
