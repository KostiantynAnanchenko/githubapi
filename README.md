# GitHub Repositories API

This is a Spring Boot application that interacts with the GitHub API to list repositories of a user that are not forks.

## Installation

1. Clone the repository.
2. Open the project in your favorite IDE.
3. Run the application.

## Running the application

1. Start the Spring Boot application.
2. Open your browser or API client and navigate to:
    ```
    http://localhost:8081/users/{username}/repos
    ```

## Example

- To get repositories of user `octocat`:
    ```
    http://localhost:8081/users/octocat/repos
    ```

## Error Handling

- If the user does not exist, the API returns a 404 response with the following structure:
    ```json
{
"customMessage": "Something went wrong, user not found. Please check the information and try again.",
"error": "Not Found",
"status": 404
}
    ```