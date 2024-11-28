Listing & User has a relation where Listing has a Foreign Key of User Id. Theres obviously strong relation between the two. But Listing & User is two different service inside microservice architecture. So both need to be able to run alone without the other. The demonstration is presented below.

# Listing & User Project Running
GET /listings
```
{
    "result": true,
    "listings": [
        {
            "id": 1,
            "userId": 1,
            "listingType": "rent",
            "price": 1,
            "createdAt": 1732810559052003,
            "updatedAt": 1732810559052003
        }
    ]
}
```
GET /users
```
{
    "result": true,
    "users": [
        {
            "id": 1,
            "name": "user1",
            "createdAt": 1732810595769926,
            "updatedAt": 1732810595769926
        }
    ]
}
```

# Listing Project Running
GET /listings
```
{
    "result": true,
    "listings": [
        {
            "id": 1,
            "userId": 1,
            "listingType": "rent",
            "price": 1,
            "createdAt": 1732810559052003,
            "updatedAt": 1732810559052003
        }
    ]
}
```
GET /users

![image](https://github.com/user-attachments/assets/a946db6e-a67f-4d7f-bcf6-7b74f1acb9d2)

# User Project Running
GET /listings

![image](https://github.com/user-attachments/assets/a09af024-ad10-447e-8dee-d516f302ef5e)

GET /users
```
{
    "result": true,
    "users": [
        {
            "id": 1,
            "name": "user1",
            "createdAt": 1732810595769926,
            "updatedAt": 1732810595769926
        }
    ]
}
```

# Conclusion
As you can see, even if one of the service is terminated, it doesnt affect other service. This is what microservice mean.
