
function (names.arg, minlength = 4L, use.classes = TRUE, dot = FALSE, 
          strict = FALSE, method = c("left.kept", "both.sides")) {
    if (minlength <= 0L) # if minLength <= 0, return ""
        return(rep.int("", length(names.arg)))
    names.arg <- sub("^ +", "", sub(" +$", "", as.character(names.arg)))
    dups <- duplicated(names.arg)
    old <- names.arg
    if (any(dups)) 
        names.arg <- names.arg[!dups]
    x <- names.arg
    if (strict) {
        x[] <- .Internal(abbreviate(x, minlength, use.classes))
    }
    else {
        method <- match.arg(method)
        if (method == "both.sides") 
            chRev <- function(x) sapply(lapply(strsplit(x, NULL), 
                                               rev), paste, collapse = "")
        dup2 <- rep.int(TRUE, length(names.arg))
        these <- names.arg
        repeat {
            ans <- .Internal(abbreviate(these, minlength, use.classes))
            x[dup2] <- ans
            if (!any(dup2 <- duplicated(x))) 
                break
            if (method == "both.sides") {
                x[dup2] <- chRev(.Internal(abbreviate(chRev(names.arg[dup2]), 
                                                      minlength, use.classes)))
                if (!any(dup2 <- duplicated(x))) 
                    break
            }
            minlength <- minlength + 1
            dup2 <- dup2 | match(x, x[dup2], 0L)
            these <- names.arg[dup2]
        }
    }
    if (any(dups)) 
        x <- x[match(old, names.arg)]
    if (dot) {
        chgd <- x != old
        x[chgd] <- paste0(x[chgd], ".")
    }
    names(x) <- old
    x
}

