<p align="center">
  <img src="https://www.spigotmc.org/data/resource_icons/118/118792.jpg?1723289030">
  <h1 align="center">Redeemables</h1>
  <h5 align="center">Reward codes in Minecraft</h5>
</p>

Redeemables is a plugin that allows you to make reward codes that can be redeemed by your players.
Let's say your server has an affiliate partner (e.g. a content creator) that wants to give it's following a reward on your server, attracting new players.
This can easily be done with Redeemables as shown below. You can set a use limit so only a given amount of players can use a reward code so the reward becomes even more exclusive.
The plugin is fast and doesn't slow down your server, so what are you waiting for?

## Configuration / adding reward codes

As of release `1.0-rc1.1`, reward codes can be configured/added in the plugin's configuration file (`plugins/Redeemables/config.yml`).
With the plugin comes an example reward code that can be used as a template to add your own reward codes.

The default configuration file:
```yml
example-redeemable: # Reward code
  commands: # list of commands to execute when the reward is redeemed.
  - 'say %PLAYER% redeemed the example-redeemable!' # %PLAYER% will be replace with the reward receiver's ingame-name
  uses: 10 # The use limit for the code. In this case only 10 players can redeem 'example-redeemable'
```

When a player redeems a reward code, the configuration file will be updated, adding a `used_by` value to the redeemable, followed by the receiver's UUID.
Example:

```yml
example-redeemable:
  commands:
  - 'say %PLAYER% redeemed the example-redeemable!'
  uses: 10
  used_by: # This is added automatically when the first player redeems the reward
  - 2d81981c-740d-4ef2-9c1e-420dad245af0 # Totally not my uuid xD
```

That's pretty much it!
