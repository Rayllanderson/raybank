package com.rayllanderson.raybank.statement.controllers.find.card;

import com.rayllanderson.raybank.statement.controllers.BankStatementDto;
import com.rayllanderson.raybank.statement.services.BankStatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/credit-card") //todo: refactor later
public class CardStatementController {

    private final BankStatementFinderService bankStatementFinderService;

    @GetMapping("/statements")
    public ResponseEntity<List<BankStatementDto>> findStatements(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(bankStatementFinderService.findAllCreditCardBankStatementsByUserId(getUserIdFrom(jwt)));
    }
}
