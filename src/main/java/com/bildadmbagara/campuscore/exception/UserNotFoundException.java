package com.bildadmbagara.campuscore.exception;

public class UserNotFoundException extends RuntimeException{
   public UserNotFoundException(String message){
       super(message);
   }
}
