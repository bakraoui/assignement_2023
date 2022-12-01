package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.operationdto.TransferDto;

public class TransferMapper {

    private static TransferDto transferDto;

    public static TransferDto mapToTransfertDto(Transfer transfer) {

        transferDto = new TransferDto();
        transferDto.setNrCompteEmetteur(transfer.getCompteEmetteur().getNumeroCompte());
        transferDto.setDate(transfer.getDateExecution());
        transferDto.setMotif(transfer.getMotif());

        return transferDto;
    }

    public static Transfer mapToTransfer(TransferDto transferDto, Compte emetteur, Compte beneficiaiare) {
        Transfer transfer = new Transfer();

        transfer.setMotif(transferDto.getMotif());
        transfer.setMontant(transferDto.getMontant());
        transfer.setCompteBeneficiaire(beneficiaiare);
        transfer.setCompteEmetteur(emetteur);

        transfer.setDateExecution(transferDto.getDate());
        transfer.setNomPrenomEmetteur(emetteur.getUtilisateur().getFirstname()+" "+emetteur.getUtilisateur().getLastname());

        return transfer;
    }
}
