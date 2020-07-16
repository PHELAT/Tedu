<img src="https://raw.githubusercontent.com/PHELAT/Tedu/master/asset/Tedu.png"/></br>
[![Build Status](https://travis-ci.org/PHELAT/Tedu.svg?branch=master)](https://travis-ci.org/PHELAT/Tedu)
[![CodeFactor](https://www.codefactor.io/repository/github/phelat/tedu/badge)](https://www.codefactor.io/repository/github/phelat/tedu)  
-
### ⤵️ Download
Tedu is currently released on Google Play, but it's released under an alpha program that is not available to public users. If you want to join the alpha program and test the app in its early stages, please drop an email at [phelatco@gmail.com](mailto:phelatco@gmail.com), and I will send you an invitation.
### 🤝 Contribute
I believe that everyone can contribute to this project, even without the knowledge in computer science and programming. So please, if you have free time, consider contributing to this project in these areas:
- **Report a bug**. If you've ever encountered a bug in the app, please consider reporting it through this [link](https://github.com/PHELAT/Tedu/issues/new?assignees=&labels=bug&template=bug_report.md&title=). If you think this is too complicated, it's totally fine, and you can report the bug in plain English to me through this [phelatco@gmail.com](mailto:phelatco@gmail.com) email address.
- **Propose a feature**. If you think a feature is missing from the app, please consider proposing it through this [link](https://github.com/PHELAT/Tedu/issues/new?assignees=&labels=enhancement&template=feature_request.md&title=). If you think this is too complicated, it's totally fine, and you can propose the feature in plain English to me through this [phelatco@gmail.com](mailto:phelatco@gmail.com) email address.
- **Translate**. If you are familiar with a language other than English, you can help to translate the app to that language, and it's pretty straight forward, you don't need to be a computer expert or anything, I'm just going to send you a list of words and sentences that we've used in the app, and you'll send us their translation. It's that simple, and to get started, send me an email([phelatco@gmail.com](mailto:phelatco@gmail.com)) so that I could send you the list of words and sentences.
- **Pull request**. If you are a software engineer and understand Kotlin, you can help and contribute to this project by working on the reported bugs, refactor the code and keep it fresh, or even implement a new feature that you think it's cool to have(But before that, make sure to contact me and describe what you want to achieve). You can also checkout the project's roadmap board from [here](https://github.com/PHELAT/Tedu/projects/1).  

**There is going to be a page inside the app with a list of all contributors/sponsors and a link to their github/social media(Of course, with their consent).**
### ❤️ Sponsor
As you know, this project is free and doesn't have annoying ads; hence, it's not profitable. But you can always keep a developer motivated by buying him/her a cup of coffee. This surely helps me to develop and maintain the app.  
[![Paypal](https://raw.githubusercontent.com/PHELAT/Tedu/master/asset/paypal.png)](https://paypal.me/mahdinouri)
[![Bitcoin](https://raw.githubusercontent.com/PHELAT/Tedu/master/asset/bitcoin.png)](https://blockchair.com/bitcoin/address/169d2AG9y8CLzqfDXar4xN7euhwGhVXEkL)

---
### ⚙️ Architecture
<img src="https://raw.githubusercontent.com/PHELAT/Tedu/master/asset/tedu_hierarchy.png"/></br>
At my current job at Cafe Bazaar, we were trying to migrate our Android project to a modular structure so we could scale our Android chapter in the long-term. This project's architecture is the result of my research and development on the idea of having a modular Android project, and I tried to solve all the challenges that one could face when migrating to a modular structure.

**Challenges: Navigation**  
<img src="https://raw.githubusercontent.com/PHELAT/Tedu/master/asset/tedu_navigation.png"/></br>
Handling navigation in a modular structure could be challenging, especially if you are using the Jetpack's Navigation Component. What I've come up with is a structure in which every feature module has its navigation graph, and their graph file resides in the platform module, in this case, the `:platform:mobile` module. Navigation inside a module is done using the Actions, and nothing differs here from a monolith project, but to navigate between modules(inter-module navigation), I've used deep-links with the help of Navigation Component's deep-linking feature. Now, this has two issues, first is that the SafeArgs plugin can't help us to have type safety when we are using deep-links and the second is that, we can't pass Parcelable/Serializable data between destinations. For the first issue, I haven't done anything because I didn't have the time to implement a Gradle plugin like SafeArgs for deep links, but for the second issue, I've implemented an extension function for the NavController called `interModuleNavigate` which receives two parameters:
1. The deep link URI that we want to navigate to.
2. The marshalable data(Parcelable/Serializable) that you want to pass to the destination.

And it's used like this:
```kotlin
findNavController().interModuleNavigate(
    getString(R.string.deeplink_userprofile).toUri(),
    userEntity
)
```
We store deep-link values in a string resource file so that we could use them in the navigation graph file for defining a deep-link destination and also use them at the runtime to navigate to that destination. Now that you've understood how the front-end of this implementation is used, let's talk about its back-end and how things look behind the curtain. Since we normally can't pass Parcelable/Serializable data using deep-links, I've decided to write the data on the disk and then read it afterward. Every deep-link that is used for inter-module navigation and requires to pass a marshallable data, needs to have a query called `extraData`. We set this query's value at the runtime, and it's a timestamp, fetched from `System.currentTimeMillis`. We use this timestamp to name the file that we are going to write on the disk, so in the destination, we would know what file to read from to access the marshalled data. Since we are passing the timestamp as part of the deep-link's URI, we don't have to worry about losing it in configuration changes or process death, because every query is saved inside Fragment's argument bundle and we know that Fragment will take care of its argument bundle automatically and stores it when `onSaveInstanceState` happens. All of this is happening inside the `:library:navigation` module if you wanted to take a look.
