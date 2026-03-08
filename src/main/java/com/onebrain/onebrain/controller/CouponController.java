package com.onebrain.onebrain.controller;

import com.onebrain.onebrain.dto.CouponRequest;
import com.onebrain.onebrain.dto.CouponResponse;
import com.onebrain.onebrain.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
@Tag(name = "Coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    @Operation(summary = "Create coupon", description = "Create a new coupon")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "created", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CouponResponse.class)
            )),
            @ApiResponse(responseCode = "400", content = @Content(
                    schema = @Schema(hidden = true)
            ))
    })
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody CouponRequest couponRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(couponService.create(couponRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get coupon", description = "Retrieve a coupon")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coupon founded", content = @Content(
                    schema = @Schema(implementation = CouponResponse.class)
            )),
            @ApiResponse(responseCode = "204", description = "Not found")
    })
    public ResponseEntity<CouponResponse> get(@PathVariable String id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(couponService.get(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a coupon", description = "Delete a coupon exists")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Coupon has already deleted"),
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        couponService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
