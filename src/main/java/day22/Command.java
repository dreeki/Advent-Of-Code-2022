package day22;

public sealed interface Command permits TurnCommand, MoveCommand {
}
