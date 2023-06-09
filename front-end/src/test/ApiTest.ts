import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';


import {describe, it} from "node:test";
import {getAllClients} from "@modules/utils/api/api-client";
import {Client, Role} from "@modules/utils/entity/interface";
import expect from "expect"; // Replace with actual path to your client module

// This sets the mock adapter on the default instance
const mock = new MockAdapter(axios);

describe('getAllClients', () => {
    it('fetches clients successfully from API', async () => {
        const data: Client[] = [
            { id: 1, role: Role.Client, email: 'clientA@example.com', mobilePhone: '1234567890', balance: 100.00 },
            { id: 2, role: Role.Client, email: 'clientB@example.com', mobilePhone: '0987654321', balance: 200.00 },
            // More clients...
        ];

        // Mock the GET request
        mock.onGet(String(process.env.getAllClients)).reply(200, data);

        // Use the function
        const clients = await getAllClients();

        expect(clients).toEqual(data);
    });
});
