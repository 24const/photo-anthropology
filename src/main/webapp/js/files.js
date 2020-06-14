let fileService = {
    findAll(fn) {
        axios
            .get('files/getAllFiles')
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
};
let List = Vue.extend({
    template: '#all-files',
    data: function () {
        return {files: [], searchKey: ''};
    },
    computed: {
        filteredFiles() {
            return this.files.filter((one_file) => {
                return one_file.id.indexOf(this.searchKey) > -1
                    || one_file.file_name.indexOf(this.searchKey) > -1
                    || one_file.date_created.indexOf(this.searchKey) > -1
            })
        }
    },
    mounted() {
        fileService.findAll(r => {
            this.files = r.data;
            files = r.data
        })
    }
});
