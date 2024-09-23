# Backup
Этот репозиторий дополняет доклад ["Backup в Android, или как сэкономить бизнесу миллионы"](https://mobiusconf.com/talks/5814ddde0a994849bb1e005922ddd0c3).
   
**Код разбит по веткам**:
- main — key-value backup
- autobackup-simple — autobackup с xml конфигурацией
- autobackup-with-backupagent — autobackup с постпроцессинговыми операциями в наследнике BackupAgent
- keyvalue-custom — простой аналог FileBackupHelper
   
**Тестирование**:
```bash
sh tools/app_backup_with_restore.sh com.artrblog.backup
```
Если скрипт не работает для вашего приложения или устройства, то создайте резервную копию в настройках системы, удалите приложение и установите заново.