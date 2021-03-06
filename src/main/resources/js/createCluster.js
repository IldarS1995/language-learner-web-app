/**
 * Created by Ildar on 30.07.2015.
 */

var swapLangs;
$(function() {
    var lang1 = $('#language1');
    var lang2 = $('#language2');

    var selectedLangs = ["", ""];

    var allowSubmitting = true;

    //Setting languages to <select>s
    setLanguages(lang1Select, lang2Select);

    /**
     * Add the language that was removed previously and remove the language that has been selected
     * in another <select>
     * @param lang Another language <select> object
     * @param idx Index in array that stored removed language for this object
     * @param val New selected language
     */
    function addAndDeleteOption(lang, idx, val) {
        if(selectedLangs[idx].localeCompare("") != 0) {
            lang.append("<option value='" + selectedLangs[idx] + "'>" + selectedLangs[idx] + "</option>");
        }

        selectedLangs[idx] = val;
        lang.children("option[value=" + val + "]").remove();
    }

    lang1.change(function() {
        addAndDeleteOption(lang2, 0, $(this).val());
        checkClusterExistence(lang1.val(), lang2.val());
    });

    lang2.change(function() {
        addAndDeleteOption(lang1, 1, $(this).val());
        checkClusterExistence(lang1.val(), lang2.val());
    });

    /**
     * Send AJAX request to the server to check if such cluster already exists.
     * If it exists, restrict creating cluster to user
     * @param lang1 First language
     * @param lang2 Second language
     */
    function checkClusterExistence(lang1, lang2) {
        $.getJSON('/cluster/checkClusterExistence',
            { lang1 : lang1, lang2 : lang2 },
            function(data) {
                if(data.exists) {
                    //Such cluster already exists
                    allowSubmitting = false;
                    $('#submitErr').text("Cluster with such language pair already exists.");
                }
                else {
                    //Cluster not found or error has happened
                    if(data.langNotFound) {
                        //One of the languages was not found
                        alert("Wrong data was sent - language " + data.langNotFound + " was not found.");
                    }
                    else {
                        allowSubmitting = true;
                        $('#submitErr').text("");
                    }
                }
            }
        )
    }

    /** Sets the selected languages to the corresponding <select>s. Each language is removed
     *  from another <select>. */
    function setLanguages(lang1Select, lang2Select) {
        addAndDeleteOption(lang2, 0, lang1Select);
        addAndDeleteOption(lang1, 1, lang2Select);
        lang1.children("option[value='" + lang1Select + "']").prop('selected', true);
        lang2.children("option[value='" + lang2Select + "']").prop('selected', true);
    }

    /** Swap the two selected languages */
    function swapLanguages() {
        setLanguages(selectedLangs[1], selectedLangs[0]);
    }
    swapLangs = swapLanguages;

    $("#createForm").submit(function(){
        if(!allowSubmitting) {
            alert("Can't submit, there are errors in your form.");
            return false;
        }
    });
});