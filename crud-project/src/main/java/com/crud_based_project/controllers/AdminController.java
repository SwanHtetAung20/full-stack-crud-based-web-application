package com.crud_based_project.controllers;

import com.crud_based_project.dtos.UserDto;
import com.crud_based_project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userDto));
    }

    @PutMapping("/upload")
    public ResponseEntity<UserDto> upload(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
   return ResponseEntity.ok(userService.upload(id,file));
    }
}
