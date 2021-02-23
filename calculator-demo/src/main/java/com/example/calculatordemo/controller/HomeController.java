package com.example.calculatordemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        System.out.println("hrllll");
        return "homepage.html";
    }

    @RequestMapping("/result?result={value}")
    public String getResult(@RequestParam("result") double res) {
        Model model = null;
        model.addAttribute("result", res);
        return "result.html";
    }

    @PostMapping("/calculate")
    public void calculate(@RequestParam("num1") Double a,
                            @RequestParam("num2") Double b,
                            @RequestParam("operator") String op,
                                  ModelMap model) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8087/calculate/"+a+"/"+b+"/"+op))
                .build();

        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());

        double result = Double.parseDouble(response.body());
        String res = response.body();

        getResult(result);

//        return new RedirectView("/result");

//        HttpSession session = null;
//        session.setAttribute("result", result);
//
//        ModelMap
//        return new ModelAndView("redirect:/result");

//        model.addAttribute("result", res);
//        return new ModelAndView("redirect:/result", model);

//        RedirectAttributes attributes = new RedirectAttributesModelMap();
//        attributes.addAttribute("result", res);
//        return new RedirectView("/result");

    }
}
