<!-- TABLE OF CONTENTS -->
<details>
  <h2>Table of Contents</h2>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
            <ul>
        <li><a href="#endpoints">Endpoints</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation-api">Installation API</a></li>
      </ul>
          <ul>
        <li><a href="#react-application">Starting React Project</a></li>
      </ul>
    </li>
    <li><a href="#user-interfaces">User Interfaces</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
     <li><a href="#contact">Contact</a></li>

  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
A basic spring boot mongodb application created with Spring Initializr.

### Built With
* [Java](https://www.oracle.com/java/)
* [IntelliJ Idea](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Lombok](https://spring.io/projects/spring-data-jpa)
* [PostgreSQL](https://www.postgresql.org)

### Endpoints
| HTTP METHOD | URI                      | Variables                           | Description                           |
|-------------|--------------------------|-------------------------------------|---------------------------------------|
| POST        | /v1/customers            |                                     | Create a new customer.                |
| PUT         | /v1/customers            |                                     | Update existing customer.             |
| DELETE      | /v1/customers/{identity} | identity: Customer identity number. | Delete existing customer.             |
| POST        | /v1/applications         |                                     | Create an application.                |
| POST        | /v1/application/check    |                                     | Check existing application for users. |

<!-- GETTING STARTED -->
## Getting Started
### Installation API
1. Clone the repo
   ```sh
   https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-bahadirseven.git
   ```
2. Docker Build: ``` docker build --tag creditsystem .```
3. Docker Run: ``` docker run -p8095:8095 creditsystem```
### React Application
1. Run: ```npm start```
<!-- GETTING STARTED -->
## User Interfaces
#### Main Page
![image](https://user-images.githubusercontent.com/35156394/151699075-95b72dc3-bee7-465d-a644-dcbb6880709c.png)
#### Customer Create
![image](https://user-images.githubusercontent.com/35156394/151699096-e57fa726-cfde-426a-8684-9516e5553c01.png)
#### Customer Update
![image](https://user-images.githubusercontent.com/35156394/151699120-d4dda813-e5f9-4f81-9129-ef7a6a8466c5.png)
#### Customer Delete
![image](https://user-images.githubusercontent.com/35156394/151699134-3ff09564-3740-4d6c-85ee-3608f0e22464.png)
#### Application Create
![image](https://user-images.githubusercontent.com/35156394/151699146-5b771e76-b3fc-4766-8ad7-a59b83e8f879.png)
#### Application Check
![image](https://user-images.githubusercontent.com/35156394/151699159-62027df9-c332-4727-8d0b-5ac09a2dc327.png)

<!-- CONTRIBUTING -->
## Contributing
Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
<h4 align="center"> OR </h4>
<p align="center"><a href="https://github.com/n11-TalentHub-Java-Bootcamp/fourth-homework-bahadirseven/issues">Create an issue</a> </p>

<!-- LICENSE -->
## License
Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- CONTACT -->
## Contact
Bahadir Seven  - https://www.linkedin.com/in/bahadirseven/

Project Link: https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-bahadirseven
