DELETE FROM application;

INSERT INTO application(id, key, name, secret, description)
VALUES
    (
        1,
        'editorial-dashboard',
        'Editorial Dashboard',
        'VC3MciBHBRlyMio0u6kuVoeq9mwasmQbEBAnSHu7MmE43ssjclE3stB3LM7ed7Pm',
        NULL
    ), (
        2,
        'authoring-frontend',
        'Authoring Frontend',
        'mC5XPkAZBrW4sG7gmi7VdV1ajX0CV5d4nVKq6Jg54v5YjrasjNWcC0haV4kEWVln',
        'Nam at purus justo. Donec lacinia justo eu nisi vestibulum fermentum.'
    ), (
        3,
        'mobile-application',
        'Mobile Application',
        'EU8ig9DWP0O41IZO420QwumGXC1sqJYE5lwvpGzUpuMoC9oj3tDeN63qBcZCBY3h',
        'Nullam ipsum orci, suscipit a nibh vel, tempus iaculis nibh.'
    ), (
        4,
        'mobile-authoring',
        'Mobile Authoring',
        'QjVpgDl7C7j9q09GMHlE5qYVsOU1VDG5ZQSgVAvmf8lhrY7HnKAzNtXeZS1fPtcS',
        NULL
    ), (
        5,
        'payment-application',
        'Payment application',
        'um1TGPBGErS2g4Woqj23VXIHYTqgJr56qXuQpu8oHS4uMAkFgi8pZc40ENXbkRcW',
        'Cras sagittis nisi nec neque eleifend, non tempor erat finibus.'
    );
