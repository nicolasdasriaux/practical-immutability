package practicalimmutability.presentation.adt.visitor;

import practicalimmutability.presentation.adt.visitor.Action.Jump;
import practicalimmutability.presentation.adt.visitor.Action.Sleep;
import practicalimmutability.presentation.adt.visitor.Action.Walk;

public interface ActionVisitor<T, R> {
    R visitSleep(Sleep sleep, T t);
    R visitWalk(Walk walk, T t);
    R visitJump(Jump jump, T t);
}
