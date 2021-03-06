# Invader Must Die / TP MOBE 2022

Mini-jeu réalisé dans le cadre de l'UE MOBE du M2 Développement Logiciel par : 
- LABADENS Julien
- CAPBLANCQ Solal
- LEMEUNIER Axel
- DEMOUGEOT Maxence

# Installation de l'application

Pour installer l'application sur un appareil Android, il faut :
- Brancher l'appareil Android sur l'ordinateur via USB avec le mode développeur et débug USB activés
- Clôner ce répertoire GitHub
- Ouvrir le projet avec Android Studio et le lancer sur l'appareil branché

Lors de la première utilisation de l'application, il faut l'autoriser à utiliser le microphone de l'appareil.

P.-S. : Le développement a été effectué avec Gradle et le sdk android 27.

# Game 

Une seule règle... survivre !

Le joueur se retrouve plongé dans une zone où des ennemis apparaissent pour le chasser. Le seul but du joueur est de tenir le plus de temps possible sans se faire attraper.
Pour cela, le joueur déplace son personnage à l'aide de l'**accéléromètre** de son appareil en le penchant dans la direction où il souhaite se déplacer. S'il est touché par une unité ennemie, la partie se termine...
Par conséquent, plus le joueur survie longtemps, plus il gagne de points.
Des outils lui sont offerts pour l'aider à survivre dans ce milieu hostile : il peut **crier** pour augmenter temporairement le gain de points et swipe dans certaines directions pour activer des pouvoirs.

# Capteurs et technologies utilisés

- Accéléromètre pour déplacer le personnage dans la zone
- Microphone pour augmenter pendant une courte durée le multiplicateur de score (tant qu'il n'est pas revenu à 1 le joueur ne peut pas utiliser cet outil)
- Touch screen pour obtenir temporairement des pouvoirs : swipe vers le haut pour devenir invincible pendant une courte période et tuer les ennemis sur son passage, swipe vers le bas pour neutraliser les ennemis autour de lui grâce à une onde de choc, swipe vers la gauche pour faire apparaître des météorites qui tuent les ennemis et enfin swipe vers la droite pour temporairement ralentir les ennemis.

De plus, pour garder une trace des scores effectués par tous les joueurs, nous avons utilisé FireBase. Donc, si l'appareil est connecté à Internet, l'utilisateur verra les meilleurs scores réalisés sur Invader Must Die.

# Git et méthodologie 

Le projet est disponible sur GitHub grâce à ce lien : https://github.com/draxlir/InvaderMustDie
Nous avons essayé de respecter le workflow GitHub Flow pour le développement de ce mini-jeu.

Contact : {prénom.nom}@master-developpement-logiciel.fr


