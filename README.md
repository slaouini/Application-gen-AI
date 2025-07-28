# 🤖 Spring Boot + OpenAI Chat App

Une application Java simple basée sur **Spring Boot** qui interagit avec l'API **OpenAI (GPT-3.5-turbo)**. Elle permet aux utilisateurs de poser des questions via une interface web et d'obtenir des réponses générées par l'intelligence artificielle.

---

## 🌐 Démo

![Demo Screenshot](screenshot.png) <!-- Remplace par ton propre screenshot si tu veux -->

---

## 📌 Fonctionnalités

- Envoyer une question à GPT-3.5
- Affichage de la réponse dans une interface HTML simple
- Utilisation du client réactif `WebClient`
- Gestion des erreurs et des tentatives (`retry`)
- Architecture propre : controller, service, vue

---

## 🧰 Technologies

- Java 17+
- Spring Boot
- Spring Web (WebClient)
- Thymeleaf
- OpenAI API
- Maven

---

## 🚀 Installation & Lancement

### 1. Cloner le projet

```bash
git clone https://github.com/slaouini/genai-app.git
cd genai-app
