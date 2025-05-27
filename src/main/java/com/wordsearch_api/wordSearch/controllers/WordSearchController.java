package com.wordsearch_api.wordSearch.controllers;

import com.wordsearch_api.wordSearch.services.WordgridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/wordgrid")
@CrossOrigin("http://127.0.0.1:5500/")
public class WordSearchController {

    @Autowired
    WordgridService wordgridService;

    @GetMapping
    public String createWordGrid(@RequestParam int xAxis, @RequestParam int yAxis, @RequestParam List<String> words){
        char[][] matrix = wordgridService.generateGrid(xAxis, yAxis, words);
        StringBuilder gridToString = new StringBuilder();

        for (char[] row : matrix) {
            for (char c : row) {
                gridToString.append(c).append(' ');
            }
            gridToString.append("\n");
        }

        return gridToString.toString();
    }
}
