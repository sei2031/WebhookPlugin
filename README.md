# WebhookPlugin

## 概要

WebhookPluginは、Minecraftサーバー内からDiscord Webhookへメッセージを送信できるPaper/Spigot系プラグインです。

`config.yml` に登録名とWebhook URLを設定しておくことで、コマンドから指定した登録名のDiscordチャンネルへメッセージを送信できます。

## 機能

- `/hoge [登録名] [内容]` でDiscord Webhookへメッセージを送信
- 登録名ごとに送信先チャンネルを変更可能
- 複数のWebhookを `config.yml` で管理可能
- `/webhookplugin reload` で設定を再読み込み可能
- 権限によるコマンド制限

## コマンド

### Webhook送信

```txt
/hoge [登録名] [内容]
```

使用例：

```txt
/hoge staff サーバーで問題が発生しました
/hoge event イベントを開始します
/hoge slot 大当たりが出ました
```

### 設定再読み込み

```txt
/webhookplugin reload
```

`config.yml` を変更した後、サーバーを再起動せずに設定を反映できます。