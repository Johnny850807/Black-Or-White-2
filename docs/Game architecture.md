# Game architecture

# Table of contents

- [Abstraction](#abstraction)
- [OOD](#ood)
- [Implementation](#implementation)

Abstraction
===

To perform a Script-Driven 2D game, we need these functions:

1. **Script-driven**: Load game scripts, parse them into Sprite objects. 
    1. **Stage scripts** - A stage is like a game the player chooses to play, which consists of many **phases**. In a phase, define where the monsters, namely AIs would be spawned and when. Some meta data like stage map, bgm... etc. 
    2. **Character scripts** - A character is a sprite which can move to any four directions in the Map, perform some attacks, special movements etc. Some meta data like hp, map, bgm... etc. 
    3. **Map scripts** - A map is the fundamental place where all the game be run on. The map script defines different kinds of terrains in the map and in which locations they are, also whether the terrains are walkable. 
    4. **Weapon scripts** - A weapon can we equipped and used by a Character. Character picks up weapons in a map. Weapon scripts should define what happens if the character uses it.
    5. **Item scripts** - Others, e.g. Bullet items, Game effects ...
2. **Game Life cycle controlling**: Update screen and sprite states in a fixed period.
3. **Game Input detect**: Detect keyboard input.
4. **Game rendering**: A game panel showing the game states.
5. **AI**: The AI sprites would know which action to take in the next period.
6. **TCP connection (plus)**: The tcp connection multiplayer game can be hold by any player. 

The abstraction layers now can be defined:

Scipts => Script Prototype Factory => Game Sprites (models) => Game Controller => Game View

![Archicture Abstraction](https://github.com/Johnny850807/Black-Or-White-2/blob/master/docs/Abstraction%20Architecture.png)

Which is a basic idea of a kind of MVC.

OOD 
===

We have defined the abstract architecture above, now in the OOD phase, we look in detail in every module.
Each module can be applied some **design patterns** to make it scalable and elegant. We explain patterns by the core classes.

## Class diagram
![Class diagram](https://github.com/Johnny850807/Black-Or-White-2/blob/master/docs/Class%20Diagram.png)

**Core class:**
1. **SpritePrototypeFactory**: An implementation of [Prototype Factory pattern](https://en.wikipedia.org/wiki/Prototype_pattern). It loads all the relevant scripts into Sprite prototypes which further can be cloned by SpriteName. (Prototype Factory is good especially for Sprites. All Sprites of the same kind can share resources via **shallow-clone**.
2. **BOWController**: //TODO
3. **Sprite**: //TODO
4. **StateSequence**: //TODO
5. **Decision (AI)**: //TODO
6. **Stage**: //TODO
7. **BOWView**: //TODO



Implementation
===

In the implementation phase, we inject the spirit into each core class.
