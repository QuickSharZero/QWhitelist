## About the plugin:
**QWhitelist** is a plugin for a whitelist of players who can connect to the server. The plugin uses the player's name to verify the player.
## Plugin features:
- In the plugin config you can configure the connection to your database. **(By default, the plugin connects to the database in the plugin folder)**
- The plugin stores all names in a database.
- Your own discord bot which can be configured in the config file.
- Full customization of activity and messages sent by the bot.
## Usage:
``/qwhitelist`` - reload config and Discord bot activity.  
``/qwhitelist add <Nickname>`` - add a player to the white list.  
``/qwhitelist remove <Nickname>`` - remove a player from the white list.  
``/qwhitelist update <DiscordID>`` - update the nickname of the player linked to the specified DiscordID.  
``/code`` - find out your unique code.  
``/code <Nickname>`` - find out the player code.  
## Discord bot commands:
``/play <nickname>`` - Adds the player's nickname to the whitelist and automatically binds it to the discord.  
``/info`` - Display information about the server that you can specify in the config file.  
``/code <code>`` - Link Discord to player's nickname. (If player was added with ``/qwhitelist add <nickname>`` command)
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
- В конфиге плагина вы можете настроить подключение к вашей базе данных. **(По умолчанию плагин подключается к базе данных в папке плагина)**
- Плагин сохраняет все имена в базе данных.
- Ваш собственный Discord бот которого можно настроить в конфиг файле.
- Полная настройка активнасти и сообщений отправляемых ботом.
## Использование:
``/qwhitelist`` - обновить конфиг плагина и активность Discord бота.  
``/qwhitelist add <nickname>`` - добавить игрока в белый список.  
``/qwhitelist remove <nickname>`` - удалить игрока из белого списка.  
``/qwhitelist update <DiscordID>`` — обновить ник игрока, привязанный к указанному DiscordID.  
``/code`` - узнать свой уникальный код.  
``/code <nickname>`` - узнать код игрока.  
## Команды Discord бота:
``/play <nickname>`` - Добавляет ник игрока в белый список и автоматически привязывает его к дискорду.  
``/info`` - Выводит информацию по серверу которую вы можете указать в конфиг файле.  
``/code <code>`` - Связать дискорд с ником игрока. (Если игрок был добалвен при помощи команды ``/qwhitelist add <nickname>``)
## Разрешения:
``qwh.*`` - Предоставляет доступ ко всем командам.  
``qwh.qwhitelist.reload`` — Доступ для перезагрузки конфига плагина.  
``qwh.qwhitelist.add`` - Доступ к добавлению игрока в белый список.  
``qwh.qwhitelist.update`` — Доступ к изменению ника игрока, используя его DiscordID.  
``qwh.qwhitelist.remove`` - Доступ к удалению игрока из белого списка.  
``qwh.code`` - Доступ к получению вашего кода. (Доступно всем по умолчанию)  
``qwh.code.admin`` - Доступ для получения кода игрока.  
