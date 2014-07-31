
function (names.arg, minlength = 4L, use.classes = TRUE, dot = FALSE, 
          strict = FALSE, method = c("left.kept", "both.sides")) {

    if (minlength <= 0L) # if minLength <= 0, return empty string of size length(names.arg)
        return(rep.int("", length(names.arg))) 

    names.arg <- sub("^ +", "", sub(" +$", "", as.character(names.arg))) #not sure
    dups <- duplicated(names.arg) #vector of duplicated indeces
    old <- names.arg #argument passed

    if (any(dups)) #extract unique elements
        names.arg <- names.arg[!dups]

    x <- names.arg #make x = names.arg

    if (strict) { #check minLength strictly
        x[] <- .Internal(abbreviate(x, minlength, use.classes))
    }

    else {

        method <- match.arg(method) #init method

        if (method == "both.sides") #unsure
            chRev <- function(x) sapply(lapply(strsplit(x, NULL), 
                                               rev), paste, collapse = "")

        dup2 <- rep.int(TRUE, length(names.arg)) #fill logical vector of size length(names.args) with true
        these <- names.arg #copy names.arg

        repeat {
            ans <- .Internal(abbreviate(these, minlength, use.classes))
            x[dup2] <- ans
            if (!any(dup2 <- duplicated(x))) 
                break
            if (method == "both.sides") { #if both.sides 
                x[dup2] <- chRev(.Internal(abbreviate(chRev(names.arg[dup2]), 
                                                      minlength, use.classes)))
                if (!any(dup2 <- duplicated(x))) 
                    break
            }
            minlength <- minlength + 1
            dup2 <- dup2 | match(x, x[dup2], 0L) #Boolean: dup2 or match(x, x[dup2], 0L)
            these <- names.arg[dup2]
        }

    }

    if (any(dups)) #any duplicates
        x <- x[match(old, names.arg)]

    if (dot) { #if dot is enabled
        chgd <- x != old
        x[chgd] <- paste0(x[chgd], ".") #append dot
    }

    names(x) <- old
    x
}

