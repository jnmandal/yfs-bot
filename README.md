# yfs bot
This is a bot for gaining an unfair advantage in your yahoo fantasy football league.

## summary
This bot uses an embedded sql database (h2) with long polling/web sockets to queue messages via external messenger services. It can be deployed and run as a standalone jar file and only requires a few environment variables be set.

## setup
* get you some JDK 1.8, and the most excellent leiningen package manager/build tool
```
yfs-bot [] :> lein -v
Leiningen 2.6.1 on Java 1.8.0_65 Java HotSpot(TM) 64-Bit Server VM
```
* add you a .env file (or declare you these ENV variables)
```
CLIENT_ID='get you a super secret sauce here'
CLIENT_SECRET='get you a super secret sauce here'
TG_BOT_TOKEN='get you a botfather token here'
```
* run you `lein deps` and `lein run`
* ???
* win your league

## features list / features wishlist
* display player stats in slack/telegram
* display match reports/scores in slack/telegram
* notifications on league trades in slack/telegram
