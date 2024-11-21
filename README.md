


# JIRA 
--https://alifiharr.atlassian.net/jira/software/projects/BIB/boards/5




# Gestion des Fermes Agricoles 🌾

## Description
Une application de gestion des fermes agricoles développée avec **Spring Boot** pour gérer les fermes, champs, arbres et récoltes en respectant des contraintes spécifiques. Ce projet vise à optimiser la productivité agricole tout en assurant une organisation efficace.

---

## Fonctionnalités

- Création et gestion de fermes avec des contraintes sur la superficie et le nombre de champs.
- Ajout d'arbres dans les champs avec gestion des contraintes d'espacement et de durée de vie.
- Association d'une seule récolte par champ et par saison.
- Calcul de la productivité des champs.
- Enregistrement des ventes liées aux récoltes.

---

## Contraintes
### Fonctionnelles
1. **Superficie minimale des champs** : 0.1 hectare (1 000 m²).
2. **Superficie maximale des champs** : Aucun champ ne peut dépasser 50% de la superficie totale de la ferme.
3. **Nombre maximal de champs** : Une ferme ne peut pas contenir plus de 10 champs.
4. **Espacement des arbres** : Densité maximale de 100 arbres/hectare (10 arbres/1 000 m²).
5. **Durée de vie des arbres** : Un arbre est productif jusqu'à 20 ans.
6. **Période de plantation** : De mars à mai uniquement.
7. **Limite saisonnière** : Une seule récolte par saison et par champ.
8. **Arbres non récoltés deux fois** : Un arbre ne peut être inclus dans plus d'une récolte pour la même saison.

---

## Technologies utilisées
- **Spring Boot** : API REST.
- **Architecture en couches** : Controller, Service, Repository, Entity.
- **Annotations Spring** : Validation des données.
- **Gestion centralisée des exceptions**.
- **Tests unitaires** : JUnit et Mockito.
- **Lombok** et **Builder Pattern**.
- **MapStruct** : Conversion entre entités, DTO et View Models.

---

## Exigences Techniques

- Développement avec **Spring Boot**.
- Utilisation d'interfaces et de leur implémentation.
- Diagramme de classes UML pour la modélisation.
- Gestion des données relationnelles (SQL).

---

## Modalités pédagogiques

- **Travail individuel**.
- **Date de lancement** : 15/11/2024.
- **Deadline** : 22/11/2024.
- **Durée totale** : 4 jours.

---

## Livrables

1. **Code source complet** (GitHub).
2. **Fichier JAR** exécutable.
3. **Diagramme de classes UML**.
4. **Lien du projet JIRA**.

---

## Préparation pour la soutenance

- **IDE** prêt avec le projet ouvert.
- **Diagramme de classes** en format image/PDF.
- **Base de données** avec au moins 5 enregistrements par table.
- **Dépôt GitHub** prêt.
- **Slides de présentation** simples et claires.

---

## Déroulement de la soutenance

1. **Introduction** :
   - Présentation du projet, objectif et utilité.
   - Description des technologies utilisées.
2. **Démonstration Postman** :
   - Création d'une ferme.
   - Ajout de champs.
   - Plantation d'arbres.
   - Enregistrement des récoltes.
   - Calcul de productivité.
  
