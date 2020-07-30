package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.exceptions;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final String defaultMessage = "Sorry but may be you are wrong . . .";

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ServerError catcherOfAnyException() {
        ServerError internalServerError = new ServerError();
        internalServerError.setMessage("Sorry. Something went wrong, try a bit later, please");
        internalServerError.setExceptionDetails("unexpected exception");
        internalServerError.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return internalServerError;
    }

    @ResponseBody
    @ExceptionHandler(ObjectNotFoundException.class)
    public ServerError objectNotFoundCatcher() {
        ServerError serverError = new ServerError();
        serverError.setMessage(defaultMessage);
        serverError.setExceptionDetails("you are trying to get not existing resource");
        serverError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return serverError;
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ServerError usernameAlreadyExistsException() {
        ServerError usernameError = new ServerError();
        usernameError.setMessage(defaultMessage);
        usernameError.setExceptionDetails("such username already exists!");
        usernameError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return usernameError;
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ServerError usernameNotFoundException() {
        ServerError usernameError = new ServerError();
        usernameError.setMessage(defaultMessage);
        usernameError.setExceptionDetails("such username not found!");
        usernameError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return usernameError;
    }

    @ResponseBody
    @ExceptionHandler(ProfileNotFoundException.class)
    public ServerError profileNotFoundException() {
        ServerError profileError = new ServerError();
        profileError.setMessage(defaultMessage);
        profileError.setExceptionDetails("such profile not found!");
        profileError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return profileError;
    }

    @ResponseBody
    @ExceptionHandler(RoleNotFoundException.class)
    public ServerError roleNotFoundException() {
        ServerError roleError = new ServerError();
        roleError.setMessage(defaultMessage);
        roleError.setExceptionDetails("such role name not found!");
        roleError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return roleError;
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ServerError userNotFoundException() {
        ServerError userError = new ServerError();
        userError.setMessage(defaultMessage);
        userError.setExceptionDetails("such user does not found!");
        userError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return userError;
    }

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    public ServerError categoryNotFoundException() {
        ServerError categoryError = new ServerError();
        categoryError.setMessage(defaultMessage);
        categoryError.setExceptionDetails("such category does not found!");
        categoryError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return categoryError;
    }

    @ResponseBody
    @ExceptionHandler(TownNotFoundException.class)
    public ServerError townNotFoundException() {
        ServerError townError = new ServerError();
        townError.setMessage(defaultMessage);
        townError.setExceptionDetails("such town does not found!");
        townError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return townError;
    }

    @ResponseBody
    @ExceptionHandler(RatingNotFoundException.class)
    public ServerError ratingNotFoundException() {
        ServerError ratingError = new ServerError();
        ratingError.setMessage(defaultMessage);
        ratingError.setExceptionDetails("such rating does not found!");
        ratingError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return ratingError;
    }

    @ResponseBody
    @ExceptionHandler(LocationNotFoundException.class)
    public ServerError locationNotFoundException() {
        ServerError locationError = new ServerError();
        locationError.setMessage(defaultMessage);
        locationError.setExceptionDetails("such location does not found!");
        locationError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return locationError;
    }

    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    public ServerError commentNotFoundException() {
        ServerError commentError = new ServerError();
        commentError.setMessage(defaultMessage);
        commentError.setExceptionDetails("such comment does not found!");
        commentError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return commentError;
    }

    @ResponseBody
    @ExceptionHandler(RegionNotFoundException.class)
    public ServerError regionNotFoundException() {
        ServerError regionError = new ServerError();
        regionError.setMessage(defaultMessage);
        regionError.setExceptionDetails("such region does not found!");
        regionError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return regionError;
    }

    @ResponseBody
    @ExceptionHandler(AnnouncementNotFoundException.class)
    public ServerError announcementNotFoundException() {
        ServerError announcementError = new ServerError();
        announcementError.setMessage(defaultMessage);
        announcementError.setExceptionDetails("such announcement does not found!");
        announcementError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return announcementError;
    }

    @ResponseBody
    @ExceptionHandler(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public ServerError usernameNotFoundError() {
        ServerError usernameError = new ServerError();
        usernameError.setMessage(defaultMessage);
        usernameError.setExceptionDetails("such username not found");
        usernameError.setHttpStatus(HttpStatus.BAD_REQUEST);
        return usernameError;
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ServerError accessDeniedException() {
        ServerError accessDeniedError = new ServerError();
        accessDeniedError.setMessage(defaultMessage);
        accessDeniedError.setExceptionDetails("unable to perform this action");
        accessDeniedError.setHttpStatus(HttpStatus.CONFLICT);
        return accessDeniedError;
    }

    @ResponseBody
    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ServerError profileAlreadyExists() {
        ServerError profileAlreadyExistsException = new ServerError();
        profileAlreadyExistsException.setMessage(defaultMessage);
        profileAlreadyExistsException.setExceptionDetails("profile for such user already exists");
        profileAlreadyExistsException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return profileAlreadyExistsException;
    }


}
