package uow.bbsc.web.page.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomerResetPasswordRequest {
    private Long id;
    private String newPassword;
    private String oldPassword;

}
