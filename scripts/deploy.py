from ape import project, networks, accounts
from ape.cli import get_user_selected_account
import click

def main():
    ecosystem_name = networks.provider.network.ecosystem.name
    network_name = networks.provider.network.name
    provider_name = networks.provider.name
    click.echo(f"You are connected to network '{ecosystem_name}:{network_name}:{provider_name}'.")

    account = accounts.test_accounts[0]
    print(account)
    if network_name != "local":
        account = get_user_selected_account()
    account.deploy(project.Token)
