## About the plugin:
**QWhitelist** is a plugin for a whitelist of players who can connect to the server. The plugin uses the player's name to verify the player.
## Plugin features:
- In the plugin config you can configure the connection to your database. **(By default, the plugin connects to the database in the plugin folder.)**
- The plugin stores all names in a database.
- In the future, you can login to your server via discord. In the database, in addition to the name column, discord and code columns are created. *(A six-digit code is generated automatically for each added player)*
## Usage:
``/qwhitelist add <Nickname>`` - add a player to the white list.  
``/qwhitelist remove <Nickname>`` - remove a player from the white list.  
``/qwhitelist update <DiscordID>`` - update the nickname of the player linked to the specified DiscordID.  
``/code`` - find out your unique code.  
``/code <Nickname>`` - find out the player code.

## Permissions:
``qwh.*`` - Gives access to all commands.  
``qwh.qwhitelist.reload`` - Access to reload the plugin config.  
``qwh.qwhitelist.add`` - Access to adding a player to the white list.  
``qwh.qwhitelist.update`` - Access to change a player's nickname using his DiscordID.  
``qwh.qwhitelist.remove`` - Access to removing a player from the white list.  
``qwh.code`` - Access to receiving your code. (Available to everyone by default)  
``qwh.code.admin`` - Access to obtain player code.  

--------------------------------------------------------------------------------------
## О плагине:
**QWhitelist** это плагин для белого списка игроков, которые могут подключаться к серверу. Плагин использует имя игрока для проверки игрока.
## Особенности:
- В конфиге плагина вы можете настроить подключение к вашей базе данных. **(По умолчанию плагин подключается к базе данных в папке плагина.)**
- Плагин сохраняет все имена в базе данных.
- В будущем вы сможете зайти на свой сервер через Discord. В базе данных помимо столбца имени, создаются столбцы discordID и кода. *(Шестизначный код генерируется автоматически для каждого добавленного игрока)*
## Использование:
``/qwhitelist add <nickname>`` - добавить игрока в белый список.
``/qwhitelist remove <nickname>`` - удалить игрока из белого списка.
``/qwhitelist update <DiscordID>`` — обновить ник игрока, привязанный к указанному DiscordID.
``/code`` - узнать свой уникальный код.
``/code <nickname>`` - узнать код игрока.

## Разрешения:
``qwh.*`` - Предоставляет доступ ко всем командам.
``qwh.qwhitelist.reload`` — Доступ для перезагрузки конфига плагина.
``qwh.qwhitelist.add`` - Доступ к добавлению игрока в белый список.
``qwh.qwhitelist.update`` — Доступ к изменению ника игрока, используя его DiscordID.
``qwh.qwhitelist.remove`` - Доступ к удалению игрока из белого списка.
``qwh.code`` - Доступ к получению вашего кода. (Доступно всем по умолчанию)
``qwh.code.admin`` - Доступ для получения кода игрока.
