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

P.-S. : Le développement a été effectué avec Gradle et le sdk android 27.

# Game 

Une seule règle... survivre !

Le joueur se retrouve plongé dans une zone où des ennemis apparaissent pour le chasser. Le seul but du joueur est de tenir le plus de temps possible sans se faire attraper.
Pour cela, le joueur déplace son personnage à l'aide de l'**accéléromètre** de son appareil en le penchant dans la direction où il souhaite se déplacer. S'il est touché par une unité ennemie, la partie se termine...
Par conséquent, plus le joueur survie longtemps, plus il gagne de points.
Des outils lui sont offerts pour l'aider à survivre dans ce milieu hostile : il peut **crier** pour augmenter temporairement le gain de points et swipe dans certaines directions pour activer des pouvoirs.

# Capteurs utilisés

- Accéléromètre pour déplacer le personnage dans la zone
- Microphone pour augmenter pendant une courte durée le multiplicateur de score (tant qu'il n'est pas revenu à 1 le joueur ne peut pas utiliser cet outil)
- Touch screen pour obtenir temporairement des pouvoirs : swipe vers le haut pour devenir invincible pendant une courte période, swipe vers le bas pour neutraliser les ennemis autour de lui, swipe vers la gauche pour faire apparaître des météorites et enfin swipe vers la droite pour temporairement ralentir les ennemis

# Git et méthodologie 

Le projet est disponible sur GitHub grâce à ce lien : https://github.com/draxlir/InvaderMustDie
Nous avons essayé de respecter le workflow GitHub Flow pour le développement de ce mini-jeu.

Contact : {prénom.nom}@master-developpement-logiciel.fr


