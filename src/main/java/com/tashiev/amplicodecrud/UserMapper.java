package com.tashiev.amplicodecrud;

import com.tashiev.amplicodecrud.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDtoV1 userDtoV1);

    UserDtoV1 toUserDtoV1(User user);

    User updateWithNull(UserDtoV1 userDtoV1, @MappingTarget User user);
}