package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.operation.Transfer;
import ma.octo.assignement.dto.TransferDto;

public class TransferMapper {

    private static TransferDto transferDto;

    public static TransferDto map(Transfer transfer) {

        transferDto = new TransferDto();
        transferDto.setNrCompteEmetteur(transfer.getCompteEmetteur().getNrCompte());
        transferDto.setDate(transfer.getDateExecution());
        transferDto.setMotif(transfer.getMotif());

        return transferDto;

    }

    public static Transfer toTransfer(TransferDto transferDto, Compte emetteur, Compte beneficiaiare) {
        Transfer transfer = new Transfer();

        transfer.setMotif(transferDto.getMotif());
        transfer.setMontant(transferDto.getMontant());
        transfer.setCompteBeneficiaire(beneficiaiare);
        transfer.setCompteEmetteur(emetteur);

        transfer.setDateExecution(transferDto.getDate());
        transfer.setNom_prenom_emetteur(emetteur.getUtilisateur().getFirstname()+" "+emetteur.getUtilisateur().getLastname());

        return transfer;
    }
}
