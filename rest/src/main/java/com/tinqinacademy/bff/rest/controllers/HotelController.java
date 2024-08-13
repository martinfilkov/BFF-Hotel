package com.tinqinacademy.bff.rest.controllers;


import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.inforregister.GetRegisterInfoBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.inforregister.InfoRegisterBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.inforregister.InfoRegisterBFFOutputList;
import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateRoomBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFInputList;
import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFOutput;
import com.tinqinacademy.hotel.api.operations.base.HotelMappings;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController{
    private final GetRoomIdsBFFOperation getRoomIdsOperation;
    private final RoomByIdBFFOperation roomByIdOperation;
    private final BookRoomBFFOperation bookRoomOperation;
    private final UnbookRoomBFFOperation unbookRoomOperation;
    private final CreateRoomBFFOperation createRoomOperation;
    private final DeleteRoomBFFOperation deleteRoomOperation;
    private final PartialUpdateBFFOperation partialUpdateOperation;
    private final RegisterVisitorBFFOperation registerVisitorOperation;
    private final UpdateRoomBFFOperation updateRoomOperation;
    private final GetRegisterInfoBFFOperation getRegisterInfoOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned ids"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @GetMapping(HotelMappings.GET_IDS)
    public ResponseEntity<?> getIds(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam(value = "bedSize", required = false) Optional<String> bedSize,
            @RequestParam(value = "bathroomType", required = false) Optional<String> bathRoomType
    ) {
        GetRoomIdsBFFInput input = GetRoomIdsBFFInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bathroomType(bathRoomType)
                .bedSize(bedSize)
                .build();

        Either<Errors, GetRoomIdsBFFOutput> output = getRoomIdsOperation.process(input);
        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found room"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping(HotelMappings.GET_ROOM)
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        RoomByIdBFFInput input = RoomByIdBFFInput.builder()
                .id(roomId)
                .build();

        Either<Errors, RoomByIdBFFOutput> output = roomByIdOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully booked room"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(HotelMappings.BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId,
                                      @RequestBody BookRoomBFFInput request) {
        BookRoomBFFInput input = request.toBuilder()
                .roomId(roomId)
                .build();

        Either<Errors, BookRoomBFFOutput> output = bookRoomOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully unbooked room"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @DeleteMapping(HotelMappings.UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@PathVariable  String bookingId) {
        UnbookRoomBFFInput input = UnbookRoomBFFInput.builder()
                .bookingId(bookingId)
                .build();

        Either<Errors, UnbookRoomBFFOutput> output = unbookRoomOperation.process(input);

        return handleResponse(output, HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered a visitor"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(HotelMappings.REGISTER_VISITOR)
    public ResponseEntity<?> register(@RequestBody RegisterVisitorBFFInputList input) {
        Either<Errors, RegisterVisitorBFFOutput> output = registerVisitorOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned visitor"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Visitor not found")
    })
    @GetMapping(HotelMappings.INFO_REGISTRY)
    public ResponseEntity<?> infoRegistry(
            @RequestParam(value = "startDate") LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate,
            @RequestParam(value = "roomNumber") String roomNumber,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "idCardNumber", required = false) String idCardNumber,
            @RequestParam(value = "idCardValidity", required = false) String idCardValidity,
            @RequestParam(value = "idCardIssueAuthority", required = false) String idCardIssueAuthority,
            @RequestParam(value = "idCardIssueDate", required = false) String idCardIssueDate
    ) {

        InfoRegisterBFFInput input = InfoRegisterBFFInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .idCardNumber(idCardNumber)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNumber(roomNumber)
                .build();

        Either<Errors, InfoRegisterBFFOutputList> output = getRegisterInfoOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a room"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping(HotelMappings.CREATE_ROOM)
    public ResponseEntity<?> create(@RequestBody CreateRoomBFFInput input) {
        Either<Errors, CreateRoomBFFOutput> output = createRoomOperation.process(input);

        return handleResponse(output, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated room"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PutMapping(HotelMappings.UPDATE_ROOM)
    public ResponseEntity<?> update(@PathVariable("roomId") String id,
                                    @RequestBody UpdateRoomBFFInput request) {
        UpdateRoomBFFInput input = request.toBuilder()
                .roomId(id)
                .build();

        Either<Errors, UpdateRoomBFFOutput> output = updateRoomOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated room"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PatchMapping(path = HotelMappings.PARTIAL_UPDATE_ROOM)
    public ResponseEntity<?> partialUpdate(@PathVariable("roomId") String id,
                                           @RequestBody PartialUpdateRoomBFFInput request) {
        PartialUpdateRoomBFFInput input = request.toBuilder()
                .roomId(id)
                .build();

        Either<Errors, PartialUpdateRoomBFFOutput> output = partialUpdateOperation.process(input);

        return handleResponse(output, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted room"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @DeleteMapping(HotelMappings.DELETE_ROOM)
    public ResponseEntity<?> delete(@PathVariable("roomId") String id) {
        DeleteRoomBFFInput input = DeleteRoomBFFInput.builder()
                .id(id)
                .build();

        Either<Errors, DeleteRoomBFFOutput> output = deleteRoomOperation.process(input);
        return handleResponse(output, HttpStatus.ACCEPTED);
    }

}
