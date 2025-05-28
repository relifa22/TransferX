package lt.javau12.TransferX.controllers;

import jakarta.validation.Valid;
import lt.javau12.TransferX.DTO.*;
import lt.javau12.TransferX.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //userio kurimas
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto){
        UserDto created = userService.createUser(createUserDto);
        return ResponseEntity.status(201).body(created);

    }

    // child userio kurimas
    @PostMapping("/child")
    public ResponseEntity<ChildResponseDto> createChildUser(@Valid @RequestBody CreateChildDto createChildDto){
        ChildResponseDto created = userService.createChildUser(createChildDto);
        return ResponseEntity.status(201).body(created);

    }

    //visi useriai
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // useris pagal id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.of(userService.getUserById(id));

    }

    //vaikai pagal parent id
    @GetMapping("/parent/{parentId}/children")
    public ResponseEntity<List<ChildListDto>> getAllChildByParentId(@PathVariable Long parentId){
        return ResponseEntity.ok(userService.getChildrenByParentId(parentId));
    }

    //trinamas vaikas
    @DeleteMapping("/child/{id}")
    public ResponseEntity<Void> deleteChildById(@PathVariable("id") Long childId){
        boolean childDeleted = userService.deleteChildById(childId);
        return childDeleted
                ?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }

    //trinamas suauges su saskaitom
    @DeleteMapping("/adult/{id}")
    public ResponseEntity<Void> deleteAdultById(@PathVariable Long id){
        boolean deleted = userService.deleteAdultByid(id);
        return deleted
                ?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }





}
