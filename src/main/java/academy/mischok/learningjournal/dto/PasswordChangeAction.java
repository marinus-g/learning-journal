package academy.mischok.learningjournal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PasswordChangeAction {
    CHANGED("Password wurde erfolgreich geändert."),
    OLD_PASSWORD_NOT_EQUAL("Das alte Passwort stimmt nicht überein."),
    NO_PASSWORD_ENTERED("Keine Daten eingegeben"),
    TO_SHORT("Das neue Passwort ist zu kurz.");

    private final String message;
}
