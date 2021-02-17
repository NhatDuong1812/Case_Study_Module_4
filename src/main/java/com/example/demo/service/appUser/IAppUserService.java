package com.example.demo.service.appUser;


import com.example.demo.model.AppUser;
import com.example.demo.service.IService;

public interface IAppUserService extends IService<AppUser> {
    AppUser getUserByName(String name);
    AppUser getCurrentUser();

}
