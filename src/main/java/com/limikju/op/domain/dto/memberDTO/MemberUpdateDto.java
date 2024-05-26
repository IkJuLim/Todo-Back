package com.limikju.op.domain.dto.memberDTO;

import java.util.Optional;

public record MemberUpdateDto(Optional<String> name, Optional<String> nickName, Optional<Integer> age) {
}
