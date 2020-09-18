import TablePagination from "@material-ui/core/TablePagination";
import TablePaginationActions from "components/Partidas/TablePaginationActions";
import React from "react";

interface Props {
  rowsLength: number;
  rowsPerPage: number;
  page: number;
  onChangePage: (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => void;
  onChangeRowsPerPage: (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => void;
}

export default function CustomTablePagination({
  rowsLength,
  rowsPerPage,
  page,
  onChangePage,
  onChangeRowsPerPage,
}: Props) {
  return (
    <TablePagination
      rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
      colSpan={6}
      count={rowsLength}
      rowsPerPage={rowsPerPage}
      page={page}
      SelectProps={{
        inputProps: { "aria-label": "rows per page" },
        native: true,
      }}
      onChangePage={onChangePage}
      onChangeRowsPerPage={onChangeRowsPerPage}
      ActionsComponent={TablePaginationActions}
    />
  );
}
