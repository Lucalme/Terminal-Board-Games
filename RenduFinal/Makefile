# Variables
SRC_DIR = src
BUILD_DIR = classes
JAR_FILE = livrable2.jar
MAIN_ARES = ares.AresMain
MAIN_DEMETER = demeter.DemeterMain
JUNIT_JAR = junit-console.jar
TEST_DIR = test

# Liste des fichiers source
SOURCES = $(shell find $(SRC_DIR) -name "*.java")

# Règle par défaut
all: compile run-all

# Règle pour compiler toutes les classes
compile:
	@if [ ! -d "$(SRC_DIR)" ]; then \
		echo "Erreur : Le répertoire $(SRC_DIR) n'existe pas."; \
		exit 1; \
	fi
	@echo "Compilation des fichiers source..."
	mkdir -p $(BUILD_DIR)
	javac -classpath $(SRC_DIR) $(SOURCES) -d $(BUILD_DIR)

# Règle pour exécuter le mode Ares
run-ares: compile
	@echo "Exécution du mode Ares..."
	java -classpath $(BUILD_DIR) $(MAIN_ARES)

# Règle pour exécuter le mode Demeter
run-demeter: compile
	@echo "Exécution du mode Demeter..."
	java -classpath $(BUILD_DIR) $(MAIN_DEMETER)

# Règle pour exécuter le mode Demeter avec des arguments
# Exemple d'utilisation : ` make run-demeter-args ARGS="2 10 10" `
run-demeter-args: compile
	@echo "Exécution du mode Demeter avec des arguments..."
	java -classpath $(BUILD_DIR) $(MAIN_DEMETER) $(ARGS)

# Règle pour exécuter tous les modes
run-all: run-ares run-demeter
	@echo "Tous les modes ont été exécutés."

# Règle pour créer un fichier .jar
jar: compile
	@echo "Création de l'archive $(JAR_FILE)..."
	jar cfe $(JAR_FILE) $(MAIN_ARES) -C $(BUILD_DIR) .

# Règle pour exécuter le fichier .jar
run-jar: jar
	@echo "Exécution de l'archive $(JAR_FILE)..."
	java -jar $(JAR_FILE)

# Règle pour compiler les tests
compile-tests: compile
	@echo "Compilation des tests..."
	javac -classpath $(JUNIT_JAR):$(BUILD_DIR) $(TEST_DIR)/board/*.java

# Règle pour exécuter les tests
run-tests: compile-tests
	@echo "Exécution des tests..."
	java -jar $(JUNIT_JAR) -classpath $(TEST_DIR):$(BUILD_DIR) -select-class board.BoardTests

# Règle pour générer la documentation
doc:
	@echo "Génération de la documentation..."
	javadoc -quiet -d docs $(SOURCES)

# Règle pour nettoyer les fichiers compilés et la documentation
clean:
	@echo "Nettoyage des fichiers compilés et de la documentation..."
	rm -rf $(BUILD_DIR) $(JAR_FILE) docs

# Règle pour créer un fichier .jar pour Ares
ares.jar: compile
	@echo "Création de l'archive ares.jar..."
	jar cfe ares.jar $(MAIN_ARES) -C $(BUILD_DIR) .

# Règle pour créer un fichier .jar pour Demeter
demeter.jar: compile
	@echo "Création de l'archive demeter.jar..."
	jar cfe demeter.jar $(MAIN_DEMETER) -C $(BUILD_DIR) .