import { HttpResponse, http } from 'msw';

import ENDPOINTS from '@_apis/endPoints';

export const moimHandler = [
  http.get(`${ENDPOINTS.moims}`, () => {
    return HttpResponse.json({
      data: {
        moims: [
          {
            moimId: 1,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 2,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 3,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 4,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 5,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 6,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 7,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 8,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
          {
            moimId: 9,
            title: 'msw',
            date: '2222-02-02',
            time: '14:02:00',
            place: '2',
            currentPeople: 0,
            maxPeople: 2,
            authorNickname: '2',
            description: '',
            status: 'MOIMING',
            participants: [],
            comments: [],
            isZzimed: false,
          },
        ],
      },
    });
  }),
  http.get(`${ENDPOINTS.moims}/1`, () => {
    return HttpResponse.json({
      data: {
        moimId: 1,
        title: 'msw',
        date: '2222-02-02',
        time: '14:02:00',
        place: '2',
        currentPeople: 0,
        maxPeople: 2,
        authorNickname: '2',
        participants: [
          {
            nickname: '치코',
            src: '',
            role: 'moimer',
          },
          {
            nickname: '치코',
            src: '',
            role: 'moimee',
          },
          {
            nickname: '치코',
            src: '',
            role: 'moimer',
          },
        ],
        description: 'sdfsdfsd',
        status: 'MOIMING',
        comments: [
          {
            id: 0,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            src: '',
            child: [
              {
                id: 0,
                nickname: 'nickname',
                content: 'content',
                dateTime: '2023-04-04 14:00',
                src: '',
                child: [],
              },
            ],
          },
          {
            id: 3,
            nickname: 'nickname',
            content: 'content',
            dateTime: '2023-04-04 14:00',
            src: '',
            child: [
              {
                id: 4,
                nickname: 'nickname',
                content: 'content',
                dateTime: '2023-04-04 14:00',
                src: '',
                child: [],
              },
            ],
          },
        ],
      },
    });
  }),
  http.get(`${ENDPOINTS.chamyo}/all`, () => {
    return HttpResponse.json({
      data: {
        chamyos: [
          {
            nickname: '치코',
            src: '',
            role: 'moimer',
          },
          {
            nickname: '치코',
            src: '',
            role: 'moimee',
          },
          {
            nickname: '치코',
            src: '',
            role: 'moimer',
          },
        ],
      },
    });
  }),
  http.get(`${ENDPOINTS.zzim}/mine`, () => {
    return HttpResponse.json({
      data: {
        isZzimed: false,
      },
    });
  }),
  http.get(`${ENDPOINTS.chamyo}/mine`, () => {
    return HttpResponse.json({
      data: {
        role: 'MOIMER',
      },
    });
  }),
];
