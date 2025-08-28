    package com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;
    import lombok.Data;

    @Data
    public class  UsuarioCreateDTO {

        private Integer idUsuario;

        @NotBlank(message = "Não pode ser vazio")
        @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres")
        @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
                message = "O nome deve começar com letra maiúscula e conter apenas letras e espaços")
        private String nome;

        @Email(message = "Email inválido")
        @NotBlank(message = "Não pode ser vazio")
        @Size(max = 255,
                message = "O email não pode ter mais de 255 caracteres")
        private String email;

        @NotBlank(message = "Não pode ser vazio")
        @Size(min = 6, max = 255,
                message = "A senha deve ter entre 6 e 255 caracteres")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,255}$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula e um dígito")
        private String senha;

    }
