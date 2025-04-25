import os
import javalang
from collections import defaultdict

# Fonction utilitaire pour lire et parser les fichiers Java
def parse_java_file(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8') as file:
            content = file.read()
        tree = javalang.parse.parse(content)
        return tree
    except Exception as e:
        print(f"Erreur en analysant {filepath}: {e}")
        return None

# Structure pour contenir l’arbre
project_structure = defaultdict(lambda: defaultdict(dict))

# Parcours du dossier
def analyze_directory(directory):
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                full_path = os.path.join(root, file)
                tree = parse_java_file(full_path)
                if tree:
                    process_compilation_unit(tree, full_path)

# Traitement d’un fichier Java
def process_compilation_unit(tree, filepath):
    package = tree.package.name if tree.package else "default"
    for type_decl in tree.types:
        if isinstance(type_decl, javalang.tree.ClassDeclaration):
            class_info = extract_class_info(type_decl)
            class_name = class_info['name']
            project_structure[package][class_name] = class_info

# Extraction des infos d’une classe
def extract_class_info(class_decl):
    class_info = {
        'name': class_decl.name,
        'extends': class_decl.extends.name if class_decl.extends else None,
        'implements': [impl.name for impl in class_decl.implements] if class_decl.implements else [],
        'fields': [],
        'methods': []
    }

    for member in class_decl.body:
        if isinstance(member, javalang.tree.FieldDeclaration):
            for decl in member.declarators:
                if 'public' in member.modifiers:
                    class_info['fields'].append({
                        'name': decl.name,
                        'type': member.type.name if hasattr(member.type, 'name') else str(member.type)
                    })
        elif isinstance(member, javalang.tree.MethodDeclaration):
            if 'public' in member.modifiers:
                class_info['methods'].append({
                    'name': member.name,
                    'return_type': member.return_type.name if member.return_type else 'void',
                    'parameters': [(p.name, p.type.name) for p in member.parameters]
                })
    return class_info

# Génération HTML
def generate_html():
    html = ["<html><head><meta charset='utf-8'><style>body{font-family:Arial;} ul{margin-left:20px;}</style></head><body>"]
    html.append("<h1>Structure du Projet Java</h1>")
    for package, classes in project_structure.items():
        html.append(f"<h2>Package: {package}</h2><ul>")
        for class_name, class_info in classes.items():
            html.append(f"<li><strong>Classe: {class_name}</strong>")
            if class_info['extends']:
                html.append(f" (hérite de <em>{class_info['extends']}</em>)")
            if class_info['implements']:
                html.append(f" (implémente <em>{', '.join(class_info['implements'])}</em>)")
            html.append("<ul>")

            if class_info['fields']:
                html.append("<li>Propriétés publiques:<ul>")
                for field in class_info['fields']:
                    html.append(f"<li>{field['name']} : {field['type']}</li>")
                html.append("</ul></li>")

            if class_info['methods']:
                html.append("<li>Méthodes publiques:<ul>")
                for method in class_info['methods']:
                    params = ", ".join(f"{ptype} {pname}" for pname, ptype in method['parameters'])
                    html.append(f"<li>{method['return_type']} {method['name']}({params})</li>")
                html.append("</ul></li>")

            html.append("</ul></li>")
        html.append("</ul>")
    html.append("</body></html>")
    return "\n".join(html)

# Main
if __name__ == "__main__":
    dossier_a_analyser = "src"
    analyze_directory(dossier_a_analyser)

    with open("arbre_structure.html", "w", encoding="utf-8") as f:
        f.write(generate_html())

    print("Analyse terminée. Fichier HTML généré : arbre_structure.html")
