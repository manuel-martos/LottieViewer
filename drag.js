var dragAndDropListener = null;

function registerDragAndDropListener(onDragOver, onDragLeave, onDrop) {
    dragAndDropListener = {
        onDragOver: onDragOver,
        onDragLeave: onDragLeave,
        onDrop: onDrop,
    };
}

function unregisterDragAndDropListener() {
    dragAndDropListener = null;
}

function dropHandler(ev) {
    ev.preventDefault();
    const file = extractSingleFile(ev);
    if (file !== null && file !== undefined) {
        dragAndDropListener?.onDrop(file);
    };
}

function dragOverHandler(ev) {
    ev.preventDefault();
    dragAndDropListener?.onDragOver();
}

function dragLeaveHandler(ev) {
    ev.preventDefault();
    dragAndDropListener?.onDragLeave();
}

function extractSingleFile(ev) {
    if (ev.dataTransfer.items && ev.dataTransfer.items.length == 1) {
        // If dropped items aren't files, reject them
        if (ev.dataTransfer.items[0].kind === "file") {
            return ev.dataTransfer.items[0].getAsFile();
        }
    } else if (ev.dataTransfer.files.length == 1) {
        return ev.dataTransfer.files[0];
    }
    return undefined;
}
