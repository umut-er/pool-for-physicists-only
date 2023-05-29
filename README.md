# Pool for Physicists Only

## Introduction
Welcome to our project! Pool for Pyhsicists Only is a realistic desktop game of billards. It utilizes database
technology to sign up and login to keep track of users progress and past victories.

## How to Play the Game
The game follows the standard 9 ball pool rules. You can create and delete accounts in order to track your progress in the game.
Your account's level increases as you win more games. Players' turns are marked by a red dot next to their names.
There are a few components in the game which can be used to alter your shot.
### Sign Up/Login
When you first enter the game, you are greeted with a menu in which you have the option to login, sign-up, delete your account or change your password.
<img width="889" alt="MainMenu" src="https://github.com/umut-er/pool-for-physicists-only/assets/114430156/79a7b10e-ad6f-40de-9911-c6136c1bf6a9">

#### Signing Up/Deleting Account
While signing up, you need to pick an username and a password and an answer to a back-up security question in order to create 
an account. You can also delete a previous account from the same screen if you wish to start over from a new account.
<img width="886" alt="SignUp" src="https://github.com/umut-er/pool-for-physicists-only/assets/114430156/bfe35356-0916-498f-97c4-d513d134d1da">
#### Changing Password
If you forget your password, you can change it by entering in your username and security question's answer.
<img width="887" alt="PasswordChange" src="https://github.com/umut-er/pool-for-physicists-only/assets/114430156/ae4ae41b-5e31-420f-a0d9-1e6366566776">

#### Logging in
Once you have an account, you can enter into the game from the login screen and start playing.
<img width="887" alt="Login" src="https://github.com/umut-er/pool-for-physicists-only/assets/114430156/7d116215-0ddd-4fb9-8447-e011d117bd5d">

### Game Components
There are various components in the game you can use to create your shot.

#### Cue Stick
You can change the position you of the cue stick by moving your mouse controller. Once you've picked a position, you can stop cue stick's movement with the 'w' key or restart it with the 's' key.
![CueStick](https://github.com/umut-er/pool-for-physicists-only/assets/114430156/3af1c7ec-6c14-4c73-9f4a-11247bbae6d0)

#### Power Bar
You can change your shot's power through the power bar which effects your cue ball's initial speed.
![PowerBar](https://github.com/umut-er/pool-for-physicists-only/assets/114430156/b5961ba2-904d-4024-b1e9-90843cc38859)


#### Ball Position
You can choose which part of the part you want to hit through the cue ball image component. The part that you choose gets marked by a red square and it results in different types of spin.
![BallPosition](https://github.com/umut-er/pool-for-physicists-only/assets/114430156/4d840ceb-5d15-4895-a2c1-ab1b5d42d901)

#### Elevation Bar
The degree at which you hit the cue ball can be changed between 0 and 45 degrees through the elevation bar. Different angles can create different types of spins on the cue ball.
![ElevationBar](https://github.com/umut-er/pool-for-physicists-only/assets/114430156/5e35127b-8e76-4ccc-811e-83005451302f)

#### Hit Button
Once you're ready to make your shot, you can click the hit button to make your shot.
![HitButton](https://github.com/umut-er/pool-for-physicists-only/assets/114430156/da1bb8e6-cf44-436e-9f90-dd88db6a50b6)

#### Pause and Resume Buttons
If you wanna take a break from the game, you can pause the game and continue playing at a different time.
![PauseResume](https://github.com/umut-er/pool-for-physicists-only/assets/114430156/99b256ea-8668-4e49-a5e7-106e9b8b9101)

## Dependencies Libraries and Other Sources
- firebase4j-master
- hamcrest-core-1.3
- json-20230227 
- junit-4.13.2
- Evan Kiefl's pooltool accessible [here](https://ekiefl.github.io/projects/pooltool/)







