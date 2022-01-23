
# Soundwave - Music Streaming App

It has been made from a collective of students from International Hellenic University - Serres Campus for the purpose of the lesson 
"Τεχνολογία Λογισμικού - Εργαστήριο" - "Software Technology - Lab".


## Acknowledgements

 - [YouTube Audio Server](https://github.com/codealchemist/youtube-audio-server)
 - [YouTube Music API Node.js](https://github.com/vladdenisov/ytmusic-api)
 - [Node.js backend hosted on heroku code](https://github.com/J-h-o/node.js-server-to-talk-with-youtube-music-api-npm-plugin)
 - [Node.js server hosted on heroku for YouTube Music API Calls](https://yma-server.herokuapp.com/)
 - [Node.js server hosted on heroku for YouTube Song Streaming](https://stream-server-youtube.herokuapp.com/)


## Tech Stack

**Client:** Android, Java

**Server:** Node, Express


## API Reference

#### Get Artist

```http
  GET /artist/${ArtistName}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `ArtistName` | `string` | **Required**. Retrieves artist data from Youtube Music |

#### Get Album

```http
  GET /artist/${browseId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `browseId` | `string` | **Required**. Retrieves album data from Youtube Music using browseId retrieved from previous query |

#### Get Playlist

```http
  GET /playlist/${PlaylistId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `PlaylistId`      | `string` | **Required**. Retrieves songs and information about them from playlists hosted on Youtube Music |

#### Search

```http
  GET /search/${query}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `query`      | `string` | **Required**. Retrieves search results that would normally be shown to a user on Youtube Music |

#### Cloud Firestore

Used to save user information (like e-mail, name and phone number). 
Stores listen history and liked songs for each user individually.
Stores user created playlists and songs added to them.
Stores curated playlists that are shown on the search page.

## Testing

Tests are saved to the local directory of the application. Built using `JUnit4` and `Mockito`.



## Contributing

Contributions are closed. :(

This is a university project and therefore we do not accept the help, assistance and improvements others may want to offer at this time unfortunatelly.

However, feel free to `fork or clone` this project and use it as a base for yours 

