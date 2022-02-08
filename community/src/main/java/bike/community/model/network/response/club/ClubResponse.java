package bike.community.model.network.response.club;


import bike.community.model.entity.club.ClubUser;
import bike.community.model.entity.club.Gender;
import bike.community.model.entity.user.User;
import bike.community.model.network.response.user.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class ClubResponse {

    private String name;
    private String description;
    private boolean isPublic;
    private int age;
    private String city;
    private String bikeModel;
    private Gender gender;
    private int numOfUser;
    private User captain;
    private List<UserResponse> users;
}
